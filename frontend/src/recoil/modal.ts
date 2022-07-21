import { atom } from 'recoil';

export const isShowModalState = atom({
  key: 'modal/isShowModalState',
  default: false,
});

export const modalComponentState = atom<React.ReactElement | ''>({
  key: 'modal/modalComponentState',
  default: '',
});

export const isShowToastState = atom({
  key: 'toast/isShowToastState',
  default: false,
});

export const toastComponentState = atom<React.ReactElement | ''>({
  key: 'toast/toastComponentState',
  default: '',
});
