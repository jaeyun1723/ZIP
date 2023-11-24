import { useQuery } from 'vue-query';
import { NOTICE_LIST } from '@/constants/queryKeys';
import noticeAPI from '@/apis/noticeAPI';

const useNoticeList = (options = {}) => {
  return useQuery([NOTICE_LIST], () => noticeAPI.list(), {
    ...options,
    select: (data) => data.data.object.pageList,
  });
};

export default useNoticeList;
