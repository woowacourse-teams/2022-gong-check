import { useSetRecoilState } from 'recoil';

import { isShowToastState, toastState } from '@/recoil/toast';

const TOAST_ACTIVE_TIME = 2000;

const useToast = () => {
  const setState = useSetRecoilState(toastState);
  const setIsShowToast = useSetRecoilState(isShowToastState);

  const openToast = (type: 'SUCCESS' | 'ERROR', text: string) => {
    setIsShowToast(true);
    setState({
      type,
      text,
    });
    setTimeout(() => {
      setIsShowToast(false);
    }, TOAST_ACTIVE_TIME);
  };

  const closeToast = () => {
    setIsShowToast(false);
  };

  return { openToast, closeToast };
};

export default useToast;
