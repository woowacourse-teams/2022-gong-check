import { useRef, useState } from 'react';

import useOnContainerScroll from '@/hooks/useOnContainerScroll';

const useUserViewSection3 = () => {
  const [eventNumber, setEventNumber] = useState(0);
  const sectionRef = useRef<HTMLDivElement>(null);

  const { dimension, scrollInfo } = useOnContainerScroll(sectionRef, () => {
    const progress = Math.min(Math.max(0, scrollInfo.scrollY / dimension.windowHeight), 1) * 100;
    if (progress === 0) return;

    if (progress >= 50) {
      setEventNumber(1);
      return;
    }
    setEventNumber(0);
  });

  return { sectionRef, eventNumber };
};

export default useUserViewSection3;
