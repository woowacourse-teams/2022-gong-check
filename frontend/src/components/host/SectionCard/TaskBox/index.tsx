import useTaskBox from './useTaskBox';
import { BiMinus } from '@react-icons/all-files/bi/BiMinus';
import { BiNews } from '@react-icons/all-files/bi/BiNews';

import { TaskType } from '@/types';

import styles from './styles';

interface TaskBoxProps {
  task: TaskType;
  taskIndex: number;
  sectionIndex: number;
}

const TaskBox: React.FC<TaskBoxProps> = ({ task, taskIndex, sectionIndex }) => {
  const { onChangeInput, onClickDeleteButton, onClickTaskDetail, hasTaskDetailInfo } = useTaskBox(
    taskIndex,
    sectionIndex
  );

  return (
    <div css={styles.taskBox}>
      <div>
        <BiMinus css={styles.deleteButton} size={18} onClick={onClickDeleteButton} />
        <input
          css={styles.input}
          placeholder="새 작업"
          defaultValue={task.name}
          maxLength={10}
          onChange={onChangeInput}
          required
        />
      </div>
      <BiNews css={styles.detailButton(hasTaskDetailInfo())} size={26} onClick={onClickTaskDetail} />
    </div>
  );
};

export default TaskBox;
