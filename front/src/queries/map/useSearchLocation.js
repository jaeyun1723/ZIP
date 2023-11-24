import { useMutation } from 'vue-query';
import mapAPI from '@/apis/mapAPI';

const useSearchLocation = (options = {}) => {
  return useMutation((payload) => mapAPI.location(payload), {
    ...options,
  });
};

export default useSearchLocation;
