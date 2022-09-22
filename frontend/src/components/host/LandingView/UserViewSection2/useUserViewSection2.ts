import { useRef, useState } from 'react';

import useOnContainerScroll from '@/hooks/useOnContainerScroll';

const useUserViewSection2 = () => {
  const sectionRef = useRef<HTMLDivElement>(null);
  const [eventNumber, setEventNumber] = useState(0);

  const { dimension, scrollInfo } = useOnContainerScroll(sectionRef, () => {
    const progress = Math.min(Math.max(0, scrollInfo.scrollY / dimension.windowHeight), 1) * 100;
    if (progress === 0) return;

    if (progress >= 60 && progress < 100) {
      setEventNumber(1);
      return;
    }
    if (progress >= 100) {
      setEventNumber(2);
      return;
    }
    setEventNumber(0);
  });

  return { sectionRef, eventNumber };
};

export default useUserViewSection2;
