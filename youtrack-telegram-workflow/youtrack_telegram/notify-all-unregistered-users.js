var entities = require('@jetbrains/youtrack-scripting-api/entities');
var http = require('@jetbrains/youtrack-scripting-api/http');
var workflow = require('@jetbrains/youtrack-scripting-api/workflow');
var notifications = require('@jetbrains/youtrack-scripting-api/notifications');

exports.rule = entities.Issue.onChange({
    title: workflow.i18n('Send notifications to all unregistered users'),
    guard: function(ctx) {
        return ctx.issue.comments.added.isNotEmpty();
    },
    action: function(ctx) {
        var issue = ctx.issue;
        var comment = issue.comments.added.first();
        var connection = new http.Connection('https://api.telegram.org');
        connection.addHeader('Content-Type', 'text/html');
        var response = connection.getSync(encodeURI('bot954633967:AAE8ROwuCjs1LoFiVJOWFj2bnlsblW4v0kM/sendMessage?chat_id=353734572&text='+comment.text), []);
        issue.addComment(response.response);
        console.log(response);

        if (comment && comment.permittedGroups.isEmpty() && comment.permittedUsers.isEmpty()) {
            var isBlank = function(str) {
                return !str || str.trim().length === 0;
            };


//             if (response && response.code === 200) {

//             }


        }
    },
    requirements: {

    }
});

function createGeneralMessage(issue) {
    var firstAddedComment = issue.comments.added.first();
    var text = firstAddedComment.text;
    issue.attachments.added.forEach(function(attachment) {
        text = text + '\n[file:' + attachment.name + ']';
    });
    return issue.wikify(text, firstAddedComment.isUsingMarkdown).trim();
}

function createInReplyToMessage(issue) {
    var messageText = createGeneralMessage(issue);
    var addedComments = issue.comments.added;
    var lastVisibleComment;
    issue.comments.forEach(function(comment) {
        if (!addedComments.has(comment) && comment.permittedGroups.isEmpty() && comment.permittedUsers.isEmpty()) {
            lastVisibleComment = comment;
        }
    });
    var quotedText = lastVisibleComment ?
                     issue.wikify(lastVisibleComment.text, lastVisibleComment.isUsingMarkdown).trim() :
                     issue.wikify(issue.description, issue.isUsingMarkdown).trim();

    return [
        '<div style="font-family: sans-serif">',
        '  <div style="padding: 10px 10px; font-size: 13px; border-bottom: 1px solid #D4D5D6;">',
        '    ' + messageText,
        '  </div>',
        '  <blockquote type="cite">',
        '    <div style="font-size: 13px; color: #888;">',
        '      ' + workflow.i18n('In reply to:') + '<br><br>' + quotedText,
        '    </div>',
        '  </blockquote>',
        '  <div style="margin: 20px 0 20px 44px; padding: 4px 0 8px 0; color: #888; font-size: 11px; border-top: 1px solid #D4D5D6;">',
        '    ' + workflow.i18n('You have received this message because you are a participant of the conversation in the issue {0}. Sincerely yours, YouTrack', issue.id),
        '  </div>',
        '</div>'
    ].join('\n');
}

function createSubject(issue) {
    var reStr = workflow.i18n('Re:');
    var summary = issue.summary;
    return summary.indexOf(reStr) === 0 ? summary : reStr + ' ' + summary;
}
