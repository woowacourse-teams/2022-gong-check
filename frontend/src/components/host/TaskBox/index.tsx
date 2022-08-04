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
  const { onChangeInput, onClickDeleteButton } = useTaskBox(taskIndex, sectionIndex);

  return (
    <div css={styles.taskBox}>
      <input
        css={styles.input}
        placeholder="새 작업"
        defaultValue={task.name}
        maxLength={10}
        onChange={onChangeInput}
        required
      />
      <BiTrash css={styles.trash} size={22} onClick={onClickDeleteButton} />
    </div>
  );
};

export default TaskBox;
