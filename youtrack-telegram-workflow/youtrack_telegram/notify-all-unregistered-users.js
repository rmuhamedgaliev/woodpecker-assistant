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
        var response = connection.getSync(encodeURI('bot1050558739:AAEBT5CyU7wb-SBrT4WJ9-76QHy3ul8T7u4/sendMessage?chat_id='+ issue.fields['ChatId'].name +'&text='+comment.text), []);

        console.log(response.response);

        if (comment && comment.permittedGroups.isEmpty() && comment.permittedUsers.isEmpty()) {
            var isBlank = function(str) {
                return !str || str.trim().length === 0;
            };
        }
    },
    requirements: {

    }
});
