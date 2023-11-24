import { instance } from '@/configs/api';

const houseAPI = {
  get: (code) => instance.get(`/house?aptCode=${code}`),
};
export default houseAPI;
