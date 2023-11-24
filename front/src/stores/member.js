import { defineStore } from 'pinia';
import { reactive } from 'vue';
import { useCookies } from 'vue3-cookies';

const { cookies } = useCookies();

export const useMemberStore = defineStore(
  'member',
  () => {
    const member = reactive({
      memberId: null,
      nickname: null,
      address: null,
      type: null,
    });

    const loginMember = (data) => {
      member.memberId = data.memberInfo.memberId;
      member.nickname = data.memberInfo.nickname;
      member.address = data.memberInfo.address;
      member.type = data.memberInfo.type;
    };

    const logoutMember = () => {
      member.memberId = null;
      member.nickname = null;
      member.address = null;
      member.type = null;
    };

    const updateMember = (data) => {
      member.nickname = data.nickname;
      member.address = data.address;
    };

    const getMember = () => {
      if (!cookies.get('refreshToken')) {
        logoutMember();
      }
      return member;
    };

    return { member, loginMember, logoutMember, updateMember, getMember };
  },
  {
    persist: true,
  }
);
