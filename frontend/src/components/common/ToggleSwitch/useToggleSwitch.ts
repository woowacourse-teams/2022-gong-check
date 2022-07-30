import { useState } from 'react';

const useToggleSwitch = () => {
  const [toggle, setToggle] = useState(false);

  const onClickToggle = () => {
    setToggle(prev => !prev);
  };

  return { toggle, onClickToggle };
};

export default useToggleSwitch;
