import React from 'react';
import { useSetRecoilState } from 'recoil';

import { isShowModalState, modalComponentState } from '@/recoil/modal';

const useModal = () => {
  const setIsShowModal = useSetRecoilState(isShowModalState);
  const setModal = useSetRecoilState(modalComponentState);

  const openModal = (Component: React.ReactElement | '') => {
    setIsShowModal(true);
    setModal(Component);
  };

  const closeModal = () => {
    setIsShowModal(false);
    setModal('');
  };

  return { openModal, closeModal };
};

export default useModal;
