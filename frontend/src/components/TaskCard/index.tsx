/**  @jsxImportSource @emotion/react */
import { useParams } from 'react-router-dom';

import CheckBox from '@/components/_common/Checkbox';

import apis from '@/apis';

import styles from './styles';

type TaskType = {
  id: number;
  name: string;
  checked: boolean;
};

type TaskCardProps = {
  tasks: Array<TaskType>;
  getSections: (jobId: string) => void;
};

const TaskCard = ({ tasks, getSections }: TaskCardProps) => {
  const { jobId } = useParams();

  const handleClickCheckBox = async (
    e: React.MouseEvent<HTMLElement, MouseEvent> | React.ChangeEvent<HTMLElement>,
    id: number
  ) => {
    e.preventDefault();
    await apis.postCheckTask({ taskId: id });
    await getSections(jobId as string);
  };

  return (
    <div css={styles.taskCard}>
      {tasks.map((task, id) => (
        <div key={id} css={styles.task}>
          <CheckBox
            onChange={e => handleClickCheckBox(e, task.id)}
            checked={task.checked}
            id={JSON.stringify(task.id)}
          />
          <span>{task.name}</span>
        </div>
      ))}
    </div>
  );
};

export default TaskCard;
