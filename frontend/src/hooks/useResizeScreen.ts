import { useCallback, useEffect, useRef, useState } from 'react';

import { ScreenModeType } from '@/types';

import screenSize from '@/constants/screenSize';

const useResizeScreen = () => {
  const [resize, setResize] = useState<number>(window.innerWidth);
  const [screenMode, setScreenMode] = useState<ScreenModeType>('DESKTOP');
  const timerDebounce = useRef<ReturnType<typeof setTimeout>>();

  const handleResize = useCallback(() => {
    if (timerDebounce.current) {
      clearTimeout(timerDebounce.current);
    }

    const newTimerDebounce = setTimeout(() => {
      setResize(window.innerWidth);
      clearTimeout(timerDebounce.current);
    }, 800);

    timerDebounce.current = newTimerDebounce;
  }, []);

  useEffect(() => {
    if (screenSize.TABLET < resize) {
      setScreenMode('DESKTOP');
      return;
    }

    setScreenMode('MOBILE');
  }, [resize]);

  useEffect(() => {
    window.addEventListener('resize', handleResize);

    return () => {
      window.removeEventListener('resize', handleResize);
      if (timerDebounce.current) clearTimeout(timerDebounce.current);
    };
  }, []);

  return { screenMode };
};

export default useResizeScreen;
