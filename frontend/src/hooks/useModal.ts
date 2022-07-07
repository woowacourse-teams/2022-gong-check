import { useCallback, useState } from 'react';

const useModal = (isShow: boolean) => {
  const [isShowModal, setIsShowModal] = useState(isShow);

  const openModal = useCallback(() => {
    setIsShowModal(true);
  }, [setIsShowModal]);

  const closeModal = useCallback(() => {
    setIsShowModal(false);
  }, [setIsShowModal]);

  return { isShowModal, openModal, closeModal };
};

export default useModal;
