import { instance } from '@/configs/api';

const memberAPI = {
  login: (data) => {
    return instance.post('/login', data);
  },
  logout: () => {
    return instance.post(`/logout`);
  },
  signup: (data) => {
    return instance.post('/signup', data);
  },
  update: (id, data) => instance.patch(`/members/${id}`, data),
};

export default memberAPI;
