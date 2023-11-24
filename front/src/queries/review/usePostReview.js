import { useMutation } from 'vue-query';
import reviewAPI from '@/apis/reviewAPI';

const useSignup = (options = {}) => {
  return useMutation((payload) => reviewAPI.post(payload), {
    ...options,
  });
};

export default useSignup;
