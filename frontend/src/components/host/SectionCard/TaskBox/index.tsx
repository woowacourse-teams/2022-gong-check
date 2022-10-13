import useTaskBox from './useTaskBox';
import { BiNews } from '@react-icons/all-files/bi/BiNews';
import { BiXCircle } from '@react-icons/all-files/bi/BiXCircle';

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
        <BiXCircle css={styles.deleteButton} size={18} onClick={onClickDeleteButton} />
        <input
          css={styles.input}
          placeholder="작업 내역을 입력해주세요."
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
