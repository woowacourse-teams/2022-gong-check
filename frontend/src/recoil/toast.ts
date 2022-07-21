import { atom } from 'recoil';

type ToastStateType = {
  type: 'SUCCESS' | 'ERROR' | '';
  text: string;
};

export const isShowToastState = atom({
  key: 'toast/isShowToastState',
  default: false,
});

export const toastState = atom<ToastStateType>({
  key: 'toast/toastState',
  default: {
    type: '',
    text: '',
  },
});
