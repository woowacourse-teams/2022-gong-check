const jobs = ['청소', '마감'];

const JobList = () => {
  return (
    <>
      {jobs.map((job, index) => (
        <div key={index}>{job}</div>
      ))}
    </>
  );
};

export default JobList;
