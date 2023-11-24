import { useMutation } from 'vue-query';
import memberAPI from '../../apis/memberAPI';
import { useCookies } from 'vue3-cookies';
import camelize from 'camelize';
import { useMemberStore } from '../../stores/member';
import { useRouter } from 'vue-router';

const useLogin = (hideFn, options = {}) => {
  const { cookies } = useCookies();
  const router = useRouter();

  return useMutation((payload) => memberAPI.login(payload), {
    ...options,
    onSuccess: ({ data }) => {
      data = camelize(data);
      cookies.set('accessToken', data.accessToken);
      cookies.set('refreshToken', data.refreshToken, '30d');

      const { loginMember } = useMemberStore();
      loginMember(data);
      router.go(0);
      hideFn();
    },
  });
};

export default useLogin;
