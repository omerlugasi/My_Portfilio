import path from 'path';

export function matchRule(fileName, stats, rules) {
  const ext = path.extname(fileName).toLowerCase().slice(1);

  for (const rule of rules) {
    const extensions = rule.extensions || [];
    
    const extMatch =
      rule.extensions.includes('*') ||
      rule.extensions.includes(ext);

    if (!extMatch) continue;

    if (rule.type === 'age' && !matchAge(stats, rule)) continue;

    return rule;
  }

  return null;
}

function matchAge(stats, rule) {
  const field = rule.ageBy || 'birthtime';
  const fileDate = stats[field];

  if (!fileDate) return false;

  const ageMs = Date.now() - fileDate.getTime();

  if (rule.olderThanDays) {
    return ageMs > rule.olderThanDays * 24 * 60 * 60 * 1000;
  }

  return false;
}