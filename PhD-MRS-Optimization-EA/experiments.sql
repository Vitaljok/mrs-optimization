CREATE TABLE experiments AS
SELECT *
FROM evolution
WHERE (project_id, generation) IN 
(
	SELECT project_id, MAX(generation) gen
	FROM evolution
	WHERE project_id IN (SELECT id FROM project)
	GROUP BY project_id
);


SELECT e.project_id,
	e.fitness_value,
	ea.inst_num,
	a.code
FROM experiments e 
	LEFT JOIN evolution_agent ea ON (e.id = ea.evolution_id)
	LEFT JOIN agent a ON (ea.agent_id = a.id)
ORDER BY 1, 3