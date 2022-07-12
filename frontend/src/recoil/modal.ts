import { atom } from 'recoil';

export const isShowModalState = atom({
  key: 'modal/isShowModalState',
  default: false,
});

export const modalComponentState = atom<React.ReactElement | ''>({
  key: 'modal/modalComponentState',
  default: '',
});
