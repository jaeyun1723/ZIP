import { useQuery } from 'vue-query';
import { SEARCH_REGION } from '@/constants/queryKeys';
import mapAPI from '@/apis/mapAPI';

const useSearchRegion = (keyword, options = {}) => {
  return useQuery(
    [SEARCH_REGION, keyword.value],
    () => mapAPI.region(keyword.value),
    {
      ...options,
      select: (data) => data.data.object,
    }
  );
};

export default useSearchRegion;
