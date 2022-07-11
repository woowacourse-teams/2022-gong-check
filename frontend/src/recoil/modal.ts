import { atom } from 'recoil';

export const isShowModalState = atom({
  key: 'modal/isShowModalState',
  default: false,
});

export const modalComponentState = atom<React.ReactElement | string>({
  key: 'modal/modalComponentState',
  default: '',
});
