import { useQuery } from 'vue-query';
import { HOUSE_DATA } from '@/constants/queryKeys';
import houseAPI from '@/apis/houseAPI';

const useHouseData = (code, options = {}) => {
  return useQuery([HOUSE_DATA, code], () => houseAPI.get(code), {
    ...options,
    select: (data) => {
      return {
        rating: data.data.object.ratingDto,
        dong: data.data.object.dongCodeDto,
        apt: data.data.object.houseInfoDto,
        list: data.data.object.houseDealDtoList,
      };
    },
  });
};

export default useHouseData;
