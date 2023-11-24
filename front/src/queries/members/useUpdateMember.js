import { useMutation } from 'vue-query';
import memberAPI from '../../apis/memberAPI';
import { useMemberStore } from '../../stores';

const useUpdateMember = (id, hideFn, options = {}) => {
  const { updateMember } = useMemberStore();
  return useMutation((payload) => memberAPI.update(id, payload), {
    ...options,
    onSuccess: ({ data }) => {
      hideFn();
      updateMember(data?.object);
    },
  });
};

export default useUpdateMember;
