import { instance } from '@/configs/api';

const mapAPI = {
  region: (keyword) => instance.get(`/map/search/region?keyword=${keyword}`),
  apt: (keyword) => instance.get(`/map/search/apt?keyword=${keyword}`),
  dongCode: (dongCode) => instance.get(`/map/search/${dongCode}`),
  location: (data) => instance.post('/map/search/location', data),
};

export default mapAPI;
