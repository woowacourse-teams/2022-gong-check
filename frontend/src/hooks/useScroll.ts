import { useEffect, useState } from 'react';

const useScroll = () => {
  const [scrollPosition, setScrollPosition] = useState(0);

  const updateScroll = (e: any) => {
    const newScrollPosition = (scrollY / innerHeight) * 100 || (e.target.scrollTop / innerHeight) * 100 || 0;
    setScrollPosition(newScrollPosition);
  };

  useEffect(() => {
    window.addEventListener('scroll', updateScroll, { capture: true, passive: true });

    return () => window.removeEventListener('scroll', updateScroll);
  }, []);

  return { scrollPosition };
};

export default useScroll;
