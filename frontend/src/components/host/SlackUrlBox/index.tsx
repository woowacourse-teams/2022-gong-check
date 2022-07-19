import { css } from '@emotion/react';
import { useState } from 'react';
import { useQuery } from 'react-query';

import Button from '@/components/common/Button';
import Input from '@/components/common/Input';

import slackApi from '@/apis/slack';

import styles from './styles';

const SlackUrlBox: React.FC<{ jobName: string; jobId: number }> = ({ jobName, jobId }) => {
  // const { data } = useQuery(['slackUrl', jobId], () => slackApi.getSlackUrl(jobId), { suspense: true });

  const [url, setUrl] = useState('');

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setUrl(e.target.value);
  };

  // if (data) {
  //   setUrl(data.slackUrl);
  // }

  return (
    <div css={styles.slackUrlBox}>
      <span css={styles.jobName}>{jobName}</span>
      <Input css={styles.input} defaultValue={url} placeholder="Slack URL" onChange={handleChange} />
      <Button css={styles.button}>편집</Button>
    </div>
  );
};

export default SlackUrlBox;
