import { useCallback, useState } from 'react';

const useModal = () => {
  const [isShowModal, setIsShowModal] = useState(true);

  const openModal = useCallback(() => {
    setIsShowModal(true);
  }, [setIsShowModal]);

  const closeModal = useCallback(() => {
    setIsShowModal(false);
  }, [setIsShowModal]);

  return { isShowModal, openModal, closeModal };
};

export default useModal;
