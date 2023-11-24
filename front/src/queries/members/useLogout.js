import { useMutation } from 'vue-query';
import memberAPI from '../../apis/memberAPI';
import { useCookies } from 'vue3-cookies';
import { useMemberStore } from '../../stores/member';
import { useRouter } from 'vue-router';

const useLogout = (hideFn, options = {}) => {
  const { cookies } = useCookies();
  const { logoutMember } = useMemberStore(); 
  const router = useRouter();

  return useMutation(() => memberAPI.logout(), {
    ...options,
    onSuccess: () => {
      cookies.remove('accessToken');
      cookies.remove('refreshToken');
      logoutMember();
      router.go(0);
    },
  });
};

export default useLogout;
