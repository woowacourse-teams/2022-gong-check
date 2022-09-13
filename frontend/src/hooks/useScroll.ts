import { useEffect, useState } from 'react';

const useScroll = () => {
  const [scrollPosition, setScrollPosition] = useState(0);

  const updateScroll = (e: any) => {
    setScrollPosition((scrollY / innerHeight) * 100 || (e.target.scrollTop / innerHeight) * 100);
  };

  useEffect(() => {
    window.addEventListener('scroll', updateScroll, { capture: true, passive: true });

    return () => document.removeEventListener('scroll', updateScroll);
  }, []);

  return { scrollPosition };
};

export default useScroll;
