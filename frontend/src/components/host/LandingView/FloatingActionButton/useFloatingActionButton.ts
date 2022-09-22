import { RefObject, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import useOnContainerScroll from '@/hooks/useOnContainerScroll';

const useFloatingActionButton = (mainRef: RefObject<HTMLElement>) => {
  const navigate = useNavigate();
  const [eventNumber, setEventNumber] = useState(0);

  const { scrollInfo } = useOnContainerScroll(mainRef, () => {
    if (scrollInfo.progress === 1) {
      setEventNumber(1);
      return;
    }

    setEventNumber(0);
  });

  const onClick = () => {
    navigate('/host');
  };

  return { eventNumber, onClick };
};

export default useFloatingActionButton;
