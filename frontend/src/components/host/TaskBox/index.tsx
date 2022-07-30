import useTaskBox from './useTaskBox';
import { BiPencil, BiTrash } from 'react-icons/bi';

import Button from '@/components/common/Button';

import { TaskType } from '@/types';

import styles from './styles';

interface TaskBoxProps {
  task: TaskType;
  taskIndex: number;
  sectionIndex: number;
}

const TaskBox: React.FC<TaskBoxProps> = ({ task, taskIndex, sectionIndex }) => {
  const { taskName, isEditing, inputRef, onClickEdit, onChangeInput, onConfirmInput, onClickDeleteButton } = useTaskBox(
    task,
    taskIndex,
    sectionIndex
  );

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
            <BiPencil css={styles.pencil} size={22} onClick={onClickEdit} />
            <BiTrash css={styles.trash} size={22} onClick={onClickDeleteButton} />
          </div>
        </>
      )}
    </form>
  );
};

export default TaskBox;
