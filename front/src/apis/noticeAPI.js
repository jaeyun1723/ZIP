import { instance } from '../configs/api';

const noticeAPI = {
  list: () => instance.get('/notice/board'),
};

export default noticeAPI;
