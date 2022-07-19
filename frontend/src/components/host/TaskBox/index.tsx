import { useState } from 'react';
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
  const [taskName, setTaskName] = useState(task.name);
  const [isEditing, setIsEditing] = useState(false);

  const handleChangeInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTaskName(e.target.value);
  };

  const handleClickConfirmButton = () => {
    setIsEditing(false);
    editTask(sectionIndex, taskIndex, taskName);
  };

  const handleClickEditButton = () => {
    setIsEditing(true);
  };

  const handleClickDeleteButton = () => {
    deleteTask(sectionIndex, taskIndex);
  };

  return (
    <div css={styles.taskBox}>
      {isEditing ? (
        <>
          <input value={taskName} onChange={handleChangeInput} />
          <Button css={styles.editButton} onClick={handleClickConfirmButton}>
            수정 확인
          </Button>
        </>
      ) : (
        <>
          <span>∙ {task.name}</span>
          <div>
            <BiPencil css={styles.pencil} size={20} onClick={handleClickEditButton} />
            <BiTrash css={styles.trash} size={20} onClick={handleClickDeleteButton} />
          </div>
        </>
      )}
    </div>
  );
};

export default TaskBox;
