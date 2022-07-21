import React from 'react';
import { useSetRecoilState } from 'recoil';

import { isShowToastState, toastComponentState } from '@/recoil/modal';

const useToast = () => {
  const setIsShowToast = useSetRecoilState(isShowToastState);
  const setToast = useSetRecoilState(toastComponentState);

  const openToast = (Component: React.ReactElement | '') => {
    setIsShowToast(true);
    setToast(Component);

    setTimeout(() => {
      setIsShowToast(false);
    }, 2000);
  };

  const closeToast = () => {
    setIsShowToast(false);
    setToast('');
  };

  return { openToast, closeToast };
};

export default useToast;
