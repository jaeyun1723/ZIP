import { useQuery } from 'vue-query';
import { SEARCH_DONGCODE } from '@/constants/queryKeys';
import mapAPI from '@/apis/mapAPI';

const useSearchDongCode = (dongCode, options = {}) => {
  return useQuery(
    [SEARCH_DONGCODE, dongCode],
    () => mapAPI.dongCode(dongCode),
    {
      ...options,
      select: (data) => data.data.object,
    }
  );
};

export default useSearchDongCode;
