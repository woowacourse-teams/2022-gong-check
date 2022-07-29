import { useState } from 'react';

const useToggleSwitch = () => {
  const [toggle, setToggle] = useState(false);

  const onClickSwitch = () => {
    setToggle(prev => !prev);
  };

  return { toggle, onClickSwitch };
};

export default useToggleSwitch;
