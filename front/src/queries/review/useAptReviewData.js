import { useQuery } from 'vue-query';
import { APT_REVIEW_DATA } from '../../constants/queryKeys';
import reviewAPI from '../../apis/reviewAPI';
import { useCookies } from 'vue3-cookies';

const { cookies } = useCookies();

const useAptReviewData = (aptCode, page, options = {}) => {
  return useQuery(
    [APT_REVIEW_DATA, aptCode, page.value],
    () => cookies.get('refreshToken') && reviewAPI.get(aptCode, page.value),
    {
      ...options,
      keepPreviousData: true,
      select: (data) => {
        return {
          pageList: data?.data.object.pageList,
          pagination: data?.data?.object.pagination,
        };
      },
    }
  );
};

export default useAptReviewData;
