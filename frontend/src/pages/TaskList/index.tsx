/**  @jsxImportSource @emotion/react */
import PageTitle from '../../components/_common/PageTitle';
import styles from './styles';
import TaskCard from '../../components/TaskCard';
import { css } from '@emotion/react';

const locations = [
  {
    id: 1,
    name: '대강의실',
    tasks: [
      {
        id: 1,
        name: '창문 닦기',
        isChecked: true,
      },
      {
        id: 2,
        name: '책상 닦기',
        isChecked: false,
      },
    ],
  },
  {
    id: 2,
    name: '소강의실1',
    tasks: [
      {
        id: 1,
        name: '창문 닦기',
        isChecked: false,
      },
      {
        id: 2,
        name: '창틀 닦기',
        isChecked: true,
      },
      {
        id: 3,
        name: '에어컨 끄기',
        isChecked: true,
      },
      {
        id: 4,
        name: '로비 정리',
        isChecked: true,
      },
    ],
  },
];

const TaskList = () => {
  return (
    <div css={styles.layout}>
      <PageTitle>마감 체크리스트</PageTitle>
      <div css={styles.contents}>
        <form
          css={css`
            display: flex;
            flex-direction: column;
          `}
        >
          {locations.map(location => (
            <section css={styles.location} key={location.id}>
              <p css={styles.locationName}>{location.name}</p>
              <TaskCard tasks={location.tasks} />
            </section>
          ))}
          <button type="submit">제출</button>
        </form>
      </div>
    </div>
  );
};

export default TaskList;
