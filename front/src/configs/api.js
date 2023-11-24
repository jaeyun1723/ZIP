import axios from 'axios';
import camelize from 'camelize';
import { useCookies } from 'vue3-cookies';
import mem from 'mem';

const createInstance = () => {
  return axios.create({
    baseURL: import.meta.env.VITE_API_URL,
    withCredentials: true,
    headers: { 'Content-Type': 'application/json' },
  });
};

const { cookies } = useCookies();
const instance = createInstance();

const refreshTokens = mem(
  async () => {
    try {
      let { data } = await axios.post(
        import.meta.env.VITE_API_URL + '/refresh',
        {},
        { headers: { Authorization: `Bearer ${cookies.get('refreshToken')}` } }
      );
      data = camelize(data);
      console.log(data);

      cookies.set('accessToken', data?.accessToken);
      if (data?.refreshToken) {
        cookies.set('refreshToken', data?.refreshToken, '30d');
      }
      return data?.accessToken;
    } catch (e) {
      cookies.remove('accessToken');
      cookies.remove('refreshToken');
    }
  },
  { maxAge: 1000 }
);

instance.interceptors.request.use((config) => {
  let token;

  if (config.url === '/refresh') {
    token = cookies.get('refreshToken');
  } else {
    token = cookies.get('accessToken');
  }

  if (token) config.headers.Authorization = `Bearer ${token}`;

  return config;
});

instance.interceptors.response.use(
  (res) => res,
  async (err) => {
    const {
      config,
      response: { status },
    } = err;

    if (config.url === '/refresh' || status !== 401 || config.sent) {
      return Promise.reject(err);
    }

    if (!cookies.get('refreshToken')) return Promise.reject(err);

    config.sent = true;
    const accessToken = await refreshTokens();

    if (accessToken) {
      config.headers.Authorization = `Bearer ${accessToken}`;
      return axios(config);
    }

    return Promise.reject(err);
  }
);

export { instance };
