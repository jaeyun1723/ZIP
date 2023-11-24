import { useMutation } from 'vue-query';
import memberAPI from '../../apis/memberAPI';
import { useCookies } from 'vue3-cookies';
import camelize from 'camelize';
import { useMemberStore } from '../../stores/member';

const useSignup = (hideFn, options = {}) => {
  const { cookies } = useCookies();

  return useMutation((payload) => memberAPI.signup(payload), {
    ...options,
    onSuccess: ({ data }) => {
      data = camelize(data);

      cookies.set('accessToken', data.accessToken);
      cookies.set('refreshToken', data.refreshToken, '30d');
      const { loginMember } = useMemberStore();
      loginMember(data);

      hideFn();
    },
  });
};

export default useSignup;
