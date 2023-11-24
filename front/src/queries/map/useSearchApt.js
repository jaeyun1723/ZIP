import { useQuery } from 'vue-query';
import { SEARCH_APT } from '@/constants/queryKeys';
import mapAPI from '@/apis/mapAPI';

const useSearchAPT = (keyword, options = {}) => {
  return useQuery(
    [SEARCH_APT, keyword.value],
    () => mapAPI.apt(keyword.value),
    {
      ...options,
      select: (data) => data.data.object,
    }
  );
};

export default useSearchAPT;
