import { instance } from '@/configs/api';

const reviewAPI = {
  get: (aptCode, page = 1) =>
    instance.get(`/review-board/page?aptCode=${aptCode}&page=${page}`),
  post: (data) => instance.post('/review-board', data),
};

export default reviewAPI;
