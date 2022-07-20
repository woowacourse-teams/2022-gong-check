import { useEffect, useRef, useState } from 'react';
import { BiPencil, BiTrash } from 'react-icons/bi';

import Button from '@/components/common/Button';

import styles from './styles';

interface TaskBoxProps {
  task: { name: string };
  sectionIndex: number;
  taskIndex: number;
  editTask: (sectionIndex: number, taskIndex: number, value: string) => void;
  deleteTask: (sectionIndex: number, taskIndex: number) => void;
}

const TaskBox: React.FC<TaskBoxProps> = ({ task, sectionIndex, taskIndex, editTask, deleteTask }) => {
  const inputRef = useRef<HTMLInputElement>(null);

  const [taskName, setTaskName] = useState(task.name);
  const [isEditing, setIsEditing] = useState(false);

  const onChangeInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTaskName(e.target.value);
  };

  const onClickConfirmButton = () => {
    setIsEditing(false);
    editTask(sectionIndex, taskIndex, taskName);
  };

  const onClickEditButton = () => {
    setIsEditing(true);
  };

  const onClickDeleteButton = () => {
    deleteTask(sectionIndex, taskIndex);
  };

  useEffect(() => {
    inputRef.current?.focus();
  }, [isEditing]);

  return (
    <div css={styles.taskBox}>
      {isEditing ? (
        <>
          <input css={styles.input} ref={inputRef} value={taskName} onChange={onChangeInput} />
          <Button css={styles.editButton} onClick={onClickConfirmButton}>
            수정 확인
          </Button>
        </>
      ) : (
        <>
          <span>∙ {task.name}</span>
          <div>
            <BiPencil css={styles.pencil} size={20} onClick={onClickEditButton} />
            <BiTrash css={styles.trash} size={20} onClick={onClickDeleteButton} />
          </div>
        </>
      )}
    </div>
  );
};

export default TaskBox;
