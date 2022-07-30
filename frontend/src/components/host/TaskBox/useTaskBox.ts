import { useState } from 'react';

import useEditInput from '@/hooks/useEditInput';
import useSections from '@/hooks/useSections';

import { TaskType } from '@/types';

const useTaskBox = (task: TaskType, taskIndex: number, sectionIndex: number) => {
  const { editTask, deleteTask } = useSections();

  const [taskName, setTaskName] = useState(task.name);

  const { isEditing, inputRef, confirmInput, editInput: onClickEdit } = useEditInput();

  const onChangeInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTaskName(e.target.value);
  };

  const onConfirmInput = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    confirmInput();
    editTask(sectionIndex, taskIndex, taskName);
  };

  const onClickDeleteButton = () => {
    deleteTask(sectionIndex, taskIndex);
  };

  return { taskName, isEditing, inputRef, onClickEdit, onChangeInput, onConfirmInput, onClickDeleteButton };
};

export default useTaskBox;
