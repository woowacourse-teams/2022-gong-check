/**  @jsxImportSource @emotion/react */
import CheckBox from '@/components/_common/Checkbox';

import styles from './styles';

type TaskType = {
  id: number;
  name: string;
  isChecked: boolean;
};

type TaskCardProps = {
  tasks: Array<TaskType>;
};

const TaskCard = ({ tasks }: TaskCardProps) => {
  return (
    <div css={styles.taskCard}>
      {tasks.map((task, index) => (
        <div key={index} css={styles.task}>
          <CheckBox checked={task.isChecked} id={JSON.stringify(task.id)} />
          <span>{task.name}</span>
        </div>
      ))}
    </div>
  );
};

export default TaskCard;
