import { useEffect, useRef, useState } from 'react';

const useEditInput = () => {
  const inputRef = useRef<HTMLInputElement>(null);

  const [isEditing, setIsEditing] = useState(false);

  const confirmInput = () => {
    setIsEditing(false);
  };

  const editInput = () => {
    setIsEditing(true);
  };

  useEffect(() => {
    inputRef.current?.focus();
  }, [isEditing]);

  return { isEditing, inputRef, confirmInput, editInput };
};

export default useEditInput;
