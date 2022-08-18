import { useEffect, useState } from 'react';

const useScroll = () => {
  const [scrollPosition, setScrollPosition] = useState(0);

  const updateScroll = () => {
    setScrollPosition((scrollY / screen.height) * 100);
  };

  useEffect(() => {
    window.addEventListener('scroll', updateScroll);
  }, []);

  return { scrollPosition };
};

export default useScroll;
