from github import Github

g = Github("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")

#for repo in g.get_user().get_repos():
#    print(repo.name)
    

repoTest = g.get_repo("MoonMoon2/spelling-bee-cs380")

class Developer:
    def __init__(self, name):
        self.name = ""
        self.num_commits = 0
        self.num_reviews = 0
        self.num_issuesResolved = 0
        self.num_linesAdded = 0
        self.num_linesDeleted = 0
        self.devRanking = None





# Initialize a dictionary to store the Developer objects
developers = {}

# Iterate over the commits in the repository
for commit in repo.get_commits():
    # Check if the commit has an author
    if commit.author:
        # Get the name of the author of the commit
        author_name = commit.author.name
        # Check if the Developer object exists in the developers dictionary
        if author_name not in developers:
            developers[author_name] = Developer(author_name)
        # Add to the Developer object's number of commits
        developers[author_name].num_commits += 1
        # Add to the Developer object's number of lines added and deleted
        for file in commit.files:
            developers[author_name].num_linesAdded += file.additions
            developers[author_name].num_linesDeleted += file.deletions

# Iterate over the pull requests in the repository
for pull_request in repo.get_pulls(state='all'):
    # Get the author of the pull request
    author_name = pull_request.user.login
    # Check if the Developer object exists in the developers dictionary
    if author_name not in developers:
        developers[author_name] = Developer(author_name)
    # Add to the Developer object's number of reviews
    developers[author_name].num_reviews += pull_request.get_reviews().totalCount
    # Add to the Developer object's number of issues resolved
    developers[author_name].num_issuesResolved += pull_request.get_issue_comments().totalCount

# Print the developer objects
for developer_name, developer_obj in developers.items():
    print(f"Developer {developer_name}: "
          f"{developer_obj.num_commits} commits, "
          f"{developer_obj.num_reviews} reviews completed, "
          f"{developer_obj.num_issuesResolved} issues resolved, "
          f"{developer_obj.num_linesAdded} lines added, "
          f"{developer_obj.num_linesDeleted} lines deleted.")

    
score = 0

# Generate MVP
for developer_name, developer_obj in developers.items():
    score += developer_obj.num_commits * 5
    score += developer_obj.num_reviews * 8
    score += developer_obj.num_issuesResolved * 10
    print(f"Developer: {developer_name} Score: {score}")

