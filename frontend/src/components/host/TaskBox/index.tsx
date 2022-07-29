import { useEffect, useState } from 'react';
import { BiPencil, BiTrash } from 'react-icons/bi';

import Button from '@/components/common/Button';

import useEditInput from '@/hooks/useEditInput';

import styles from './styles';

interface TaskBoxProps {
  task: { name: string };
  sectionIndex: number;
  taskIndex: number;
  editTask: (sectionIndex: number, taskIndex: number, value: string) => void;
  deleteTask: (sectionIndex: number, taskIndex: number) => void;
}

const TaskBox: React.FC<TaskBoxProps> = ({ task, sectionIndex, taskIndex, editTask, deleteTask }) => {
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

  return (
    <form css={styles.taskBox} onSubmit={onConfirmInput}>
      {isEditing ? (
        <>
          <input css={styles.input} ref={inputRef} value={taskName} onChange={onChangeInput} />
          <Button type="submit" css={styles.editButton}>
            확인
          </Button>
        </>
      ) : (
        <>
          <span onClick={onClickEdit}>∙ {task.name}</span>
          <div>
            <BiPencil css={styles.pencil} size={20} onClick={onClickEdit} />
            <BiTrash css={styles.trash} size={20} onClick={onClickDeleteButton} />
          </div>
        </>
      )}
    </form>
  );
};

export default TaskBox;
